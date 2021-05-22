(ns picasso.document.core
  (:require
  [picasso.id :refer [guuid]]
  ))

(defn create []
  {:id (guuid)
   :meta {}
   :segments []})

(defn md-segment [md]
  {:id (guuid)
   :type :md
   :data (or md "")
   :state nil
   })

(defn code-segment [kernel code]
  {:type :code
   :kernel (or kernel :clj)
   :data   {:kernel (or kernel :empty)
            :code (or code "")}
   :state {}
   })

(defn add-segments [doc segs]
  (let [segs (into []
                   (concat (:segments doc) segs))]
    (assoc doc :segments segs)))

(defn add-segment [doc segment]
  (add-segments doc [segment]))

(defn add-md [doc md]
  (->> (md-segment md) (add-segment doc)))

(defn add-code [doc kernel code]
  (->> (code-segment kernel code) (add-segment doc)))


(defn clear-all [{:keys [segments] :as doc}]
  (->> (map #(assoc % :state {}) segments)
       (into [])
       (assoc doc :segments)
  ))

