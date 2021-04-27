(ns picasso.hiccup-reagent-test
  (:require
   [clojure.test :refer :all]
   ;[cljs.test :refer-macros [async deftest is testing]]
   [picasso.paint.hiccup :refer [->reagent]]))

(def data
  [{:type :hiccup
    :content [:span {:class "clj-long"} "2"]}
   {:type :hiccup
    :content [:span {:class "clj-nil"} "nil"]}
   {:type :hiccup
    :content [:div [:span.font-bold.teal-700.mr-1 "{"]
              [:span {:class "clj-map"}
               {:type :hiccup
                :content [:div [:span.font-bold.teal-700.mr-1 "["]
                          [:span {:class "clj-vector"}
                           {:type :hiccup
                            :content [:span {:class "clj-keyword"} ":a"]}
                           {:type :hiccup, :content [:span {:class "clj-long"} "5"]}]
                          [:span.font-bold.teal-700.ml-1 "]"]]}
               {:type :hiccup
                :content [:div [:span.font-bold.teal-700.mr-1 "["]
                          [:span {:class "clj-vector"}
                           {:type :hiccup
                            :content [:span {:class "clj-keyword"} ":b"]}
                           {:type :hiccup
                            :content [:span {:class "clj-string"} "\"ttt\""]}]
                          [:span.font-bold.teal-700.ml-1 "]"]]}]
              [:span.font-bold.teal-700.ml-1 "}"]]}])

(def converted
  [[:span {:class "clj-long"} "2"]
   [:span {:class "clj-nil"} "nil"]
   [:div
    [:span.font-bold.teal-700.mr-1 "{"]
    [:span
     {:class "clj-map"}
     [:div
      [:span.font-bold.teal-700.mr-1 "["]
      [:span
       {:class "clj-vector"}
       [:span {:class "clj-keyword"} ":a"]
       [:span {:class "clj-long"} "5"]]
      [:span.font-bold.teal-700.ml-1 "]"]]
     [:div
      [:span.font-bold.teal-700.mr-1 "["]
      [:span
       {:class "clj-vector"}
       [:span {:class "clj-keyword"} ":b"]
       [:span {:class "clj-string"} "\"ttt\""]]
      [:span.font-bold.teal-700.ml-1 "]"]]]
    [:span.font-bold.teal-700.ml-1 "}"]]])

(deftest to-reagent test
  (is (= (->reagent data) converted)))



