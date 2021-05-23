(ns picasso.document.transactor
  (:require
   [taoensso.timbre :as timbre :refer [debugf info error]]
   [re-frame.core :as rf]
   [picasso.document.eval :as eval]
   [picasso.document.transact :refer [fns-lookup transact]]
   [picasso.document.position :as pos]
   [picasso.document.core :as nb]))

(rf/reg-event-db
 :doc/add
 (fn
   [db [_ {:keys [id segments] :as document}]]
   (let [db (if (:docs db)
              db
              (assoc db :docs {}))
         order (vec (map :id segments))
         document (assoc document
                         :ns       nil  ; current namespace
                         :queued   #{} ; code segments that are qued for evaluation
                         :order order
                         :active   (first order))]
     (debugf "Adding document: %s " id)
     (assoc-in db [:docs id] document))))

(rf/reg-sub
 :doc/view
 (fn [db [_ id]]
   (get-in db [:docs id])))

(rf/reg-event-db
 :doc/doc-active
 (fn [db [_ id]]
   (assoc db :doc-active id)))

#_(defn run [fun-args]
    (reset! doc (transact @doc fun-args))
    nil)

(rf/reg-event-db
 :doc/exec
 (fn [db [_ fun-args]]
   (if-let [id (:doc-active db)]
     (if-let [doc (get-in db [:docs id])]
       (assoc-in db [:docs id] (transact doc fun-args))
       (do (error ":doc/exec document not found:" id)
           db))
     (do (error ":doc/exec no active document.")
         db))))

(defn exec [fun-args]
  (rf/dispatch [:doc/exec fun-args]))

(swap! fns-lookup assoc
       ;:set-active (fn [doc id]
       ;              (assoc-in doc [:active] id))
       :eval-all (partial eval/eval-all exec)
       :eval-segment (partial eval/eval-segment-id exec)
       :remove-segment-active pos/remove-active-segment)


;; compatibility


(rf/reg-sub
 :document/current
 (fn [db _]
   (:doc-active db)))

(rf/reg-sub
 :notebook
 (fn [db _]
   (let [id (:doc-active db)]
     (get-in db [:docs id]))))

(rf/reg-sub
 :notebook/queued
 (fn [db _]
   []))

#_(reg-sub
   :notebook/queued  ; all queued segments in current notebook
   :<- [:notebook]
   (fn [notebook _]
     (get-in notebook [:queued])))

(rf/reg-sub
 :segment/queued? ; is seg-id queued ?
 (fn [_ seg-id]
   [(rf/subscribe [:notebook/queued])]) ; reuse subscription :notebook/queued
 (fn [[queued] [_ seg-id]]
   ;(info "queued: " queued)
   (some #(= seg-id %) queued)))

(rf/reg-sub
 :notebook/edit?
 (fn [db [_]]
   (or (:notebook/edit? db) false)))

(rf/reg-sub
 :notebook/segment
 :<- [:notebook]
 (fn [notebook [_ seg-id]]
   (info "xx seg id: " seg-id)
   (nb/get-segment notebook seg-id)))


