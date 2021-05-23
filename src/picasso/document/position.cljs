(ns picasso.document.position
  (:require
   [taoensso.timbre :as timbre :refer [debugf info error]]
   [re-frame.core :as rf]
   [picasso.document.core :as nb]))

(defn active-segment-id [notebook]
  (:active notebook))

(defn remove-active-segment [notebook]
  (nb/remove-segment notebook (active-segment-id notebook)))

(defn segment-ids-ordered [notebook]
  (:order notebook))

#_(defn segments-ordered [notebook]
    (let [segments (:segments notebook)
          segment-ids-ordered (:order notebook)]
      (vec (map #(get segments %) segment-ids-ordered))))

(defn segment-active [doc]
  (let [active-id (:active doc)
        segment (when (and doc active-id)
                  (nb/get-segment doc active-id))]
    segment))

(defn move [order current-segment-id direction]
  (info "move " current-segment-id direction  " order: " order)
  (let [v-indexed (map-indexed (fn [idx id] [idx id]) order)
        ;_ (info "v-indexed: " v-indexed)
        current (first
                 (filter
                  (fn [[idx id]] (= current-segment-id id))
                  v-indexed))
        ;_ (info "current: " current)
        current-idx (get current 0)
        lookup (into {} v-indexed)
        ;_ (info "lookup: " lookup)
        idx-target (case direction
                     :down (min (+ current-idx 1) (- (count order) 1))
                     :up (max 0 (- current-idx 1)))
        ;_ (info "idx-target: " idx-target)
        ]
    (info "new current: " (get lookup idx-target))
    (get lookup idx-target)))

(rf/reg-event-db
 :notebook/move
 (fn [db [_ direction id]]
   (let [fun (case direction
               :up #(move (:order %) (:active %) direction)
               :down #(move (:order %) (:active %) direction)
               :first #(first (:order %))
               :last #(last (:order %))
               :to (fn [_] id))
         assoc-active (fn [doc]
                        (assoc doc :active (fun doc)))]
     ;(dispatch [:notebook/scroll-to])
     (if fun
       (rf/dispatch [:doc/exec [assoc-active]])
       db))))

(rf/reg-sub
 :notebook/segment-active
 :<- [:notebook]
 (fn [notebook _]
   (segment-active notebook)))

; commands

(rf/reg-event-db
 :segment-active/delete
 (fn [db _]
   (info "delete active segmentl")
   (rf/dispatch [:doc/exec [:remove-segment-active]])
   db))