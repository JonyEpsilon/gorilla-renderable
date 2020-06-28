(ns picasso.paint.core
  (:require
   [com.rpl.specter :as s]
   [picasso.protocols :refer [paint]]))

(defmethod paint :hiccup [picasso-spec]
  (:content picasso-spec))

(defmethod paint :reagent [picasso-spec]
  (get-in picasso-spec [:content :hiccup]))

(defmethod paint :default [picasso-spec]
  [:h1 (str "Error! No painter found for:" picasso-spec)])

(defn picasso? [i]
  (let [{:keys [type content]} i]
    (and (map? i)
         type
         content
         (keyword? type)
         (vector? content))))

(defn ->reagent [spec]
  (loop [s spec]
    (let [t (s/transform (s/walker picasso?) paint s)]
      (if (= s t) t (recur t)))))

(comment

  (def data
    [{:type :hiccup, :content [:span {:class "clj-long"} "2"]}
     {:type :hiccup, :content [:span {:class "clj-var"} "#'user/a"]}
     {:type :hiccup, :content
      [:div
       [:span.font-bold.teal-700.mr-1 "{"]
       [:span {:class "clj-map"}
        {:type :hiccup, :content [:div [:span.font-bold.teal-700.mr-1 "["]]}]]}])

  (->reagent data)

  ; play around with specter:
  (s/select [s/ALL s/ALL #(= 0 (mod % 3))]
            [[1 2 3 4] [] [5 3 2 18] [2 4 6] [12]])
  (s/select [s/ALL s/ALL #(= 0 (mod % 3))]
            [[1 2 3 4] [] [5 3 2 18] [2 4 6] [12]])

  (s/select [s/ALL picasso?] data)

  ;(def data (s/transform (s/walker picasso?) r data))

 ; 
  )