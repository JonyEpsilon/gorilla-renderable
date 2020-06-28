(ns picasso.paint.core
  (:require
   [com.rpl.specter :as s]
   [picasso.protocols :refer [paint]]))

(defmethod paint :hiccup [picasso-spec]
  ; assumes that reagent renders hiccup
  ; which is correct, except that i des not support string-styles
  (:content picasso-spec))

(defmethod paint :default [picasso-spec]
  [:div
   [:h1 "Error! No painter found!"]
   [:p  (pr-str picasso-spec)]])

(defn picasso? [picasso-spec]
  (let [{:keys [type content]} picasso-spec]
    (and (map? picasso-spec)
         type
         (keyword? type)
         (or (not content) ;reagent-cljs can have empty content when loaded from disk
             (or (vector? content) (map? content))))))

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