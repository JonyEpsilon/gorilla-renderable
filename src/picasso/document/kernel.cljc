(ns picasso.document.kernel
  (:require
   [taoensso.timbre :as timbre :refer [debugf info error]]
   [picasso.kernel.protocol :refer [available-kernels]]
   [picasso.document.core :refer [get-segment set-kernel-segment]]
   [picasso.document.position :as pos]))

(defn toggle [active available]
  (let [v-indexed (map-indexed (fn [idx id] [idx id]) available)
        current (first
                 (filter
                  (fn [[idx id]] (= active id))
                  v-indexed))
        current-idx (get current 0)
        new-idx (if (= current-idx 0)
                  (- (count available) 1)
                  (- current-idx 1))
        active-new (get available new-idx)]
    active-new))

(defn kernel-toggle [doc id]
  (let [seg (get-segment doc id)
        kernel-current (get-in seg [:data :kernel])
        available (available-kernels)
        kernel-next (toggle kernel-current available)]
    (set-kernel-segment doc id kernel-next)))

(defn kernel-toggle-active [doc]
  (let [id (pos/active-segment-id doc)]
    (kernel-toggle doc id)))





