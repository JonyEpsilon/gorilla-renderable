(ns picasso.full-test
  (:require
   [clojure.test :refer :all]
   [picasso.default-config]
   [picasso.protocols :refer [paint render]]))

(defn full [d]
  (-> d
      render
      paint))

(deftest full-int
  (is (= (full 1) [:span {:class "clj-long"} "1"])))

(deftest full-map
  (is (= (full {:a 1})
         [:span {:class "clj-map"}
          [:span.font-bold.teal-700.mr-1 "{"]
          [:span.items
           [:span {:class "clj-long"} "1"]]
          [:span.font-bold.teal-700.ml-1 "}"]])))

(deftest full-vec
  (is (= (full [1])
         [:span {:class "clj-vector"}
          [:span.font-bold.teal-700.mr-1 "["]
          [:span.items
           [:span {:class "clj-long"} "1"]]
          [:span.font-bold.teal-700.ml-1 "]"]])))
