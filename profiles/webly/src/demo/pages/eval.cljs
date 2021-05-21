(ns demo.pages.help
 (:require
    [re-frame.core :as rf]
   [webly.web.handler :refer [reagent-page]]
 ))

(defn link-fn [fun text]
  [:a.bg-blue-300.cursor-pointer.hover:bg-red-700.m-1
   {:on-click fun} text])

(defn link-dispatch [rf-evt text]
  (link-fn #(rf/dispatch rf-evt) text))

(defn link-href [href text]
  [:a.bg-blue-300.cursor-pointer.hover:bg-red-700.m-1
   {:href href} text])

(defn help []
  [:div
   [:h1 "picasso eval"]
   [:p [link-dispatch [:bidi/goto  :demo/main] "main"]]
   [:h1 "help!"]
   
   
   ])

(defmethod reagent-page :demo/eval [& args]
  [help])
