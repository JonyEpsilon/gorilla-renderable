(ns picasso.document.transactor
  (:require
   [taoensso.timbre :as timbre :refer [debugf info error]]
   [re-frame.core :as rf]
   [picasso.document.eval :as eval]
   [picasso.document.transact :refer [fns-lookup transact]]))

(rf/reg-event-db
 :doc/add
 (fn
   [db [_ {:keys [id] :as document}]]
   (let [db (if (:docs db)
              db
              (assoc db :docs {}))]
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
       :eval-all (partial eval/eval-all exec)
       :eval-segment (partial eval/eval-segment-id exec))
