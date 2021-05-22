(ns picasso.document.core
  (:require
   [picasso.id :refer [guuid]]
   [com.rpl.specter :as s]))

(defn create []
  {:id (guuid)
   :meta {}
   :segments []})

(defn md-segment [md]
  {:id (guuid)
   :type :md
   :data (or md "")
   :state nil})

(defn code-segment [kernel code]
  {:type :code
   :kernel (or kernel :clj)
   :data   {:kernel (or kernel :empty)
            :code (or code "")}
   :state {}})

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
       (assoc doc :segments)))

;(defn update-segment-state [{:keys [segments] :as doc} seg-id])

(defn seg-with-id [target-id {:keys [id] :as segment}]
  (= id target-id))

(defn seg-with-type [target-type {:keys [type] :as segment}]
  (= type target-type))

(defn seg-code [{:keys [type] :as segment}]
  (= type :code))

(defn remove-segment [doc id]
  (s/transform
   [:segments (s/filterer (partial seg-with-id id))]
   s/NONE
   doc))

(defn clear-segment [doc id]
  (s/transform
   [:segments (s/filterer (partial seg-with-id id)) s/ALL :state]
   s/NONE
   doc))

(defn set-code-segment [doc id code]
  (s/setval
   [:segments (s/filterer (partial seg-with-id id)) s/ALL :data :code]
   code
   doc))

(defn get-segment [doc id]
  (s/select-first
   [:segments (s/filterer (partial seg-with-id id)) s/FIRST]
   doc))

(defn set-kernel-segment [doc id kernel]
  (s/setval
   [:segments (s/filterer (partial seg-with-id id)) s/ALL :data :kernel]
   kernel
   doc))

(defn set-state-segment [doc id state]
  (s/setval
   [:segments (s/filterer (partial seg-with-id id)) s/ALL :state]
   state
   doc))

(comment

  (s/setval [:a (s/compact :b :c)] s/NONE {:a {:b {:c 1}}})

  (s/setval [:a :b :c] s/NONE {:a {:b {:c 1}}})

  (require '[picasso.data.document])

  (get-segment picasso.data.document/document 2)
  (remove-segment picasso.data.document/document 3)
  (clear-segment picasso.data.document/document 2)
  (set-code-segment picasso.data.document/document 2 "(+ 99 77 55)")
  (set-state-segment picasso.data.document/document 2 {:picasso [:p 7]})
  (s/transform [:segments (s/filterer (partial seg-with-id 2))] s/NONE
               picasso.data.document/document)

  ;
  )