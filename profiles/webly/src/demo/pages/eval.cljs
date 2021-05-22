(ns demo.pages.eval
  (:require
   [re-frame.core :as rf]
   [webly.web.handler :refer [reagent-page]]
   [picasso.kernel.picasso :refer [picasso-result]]
   [picasso.kernel.cell :refer [eval-cell]]
   [picasso.data.paint :as data]
   ))

(defn link-fn [fun text]
  [:a.bg-blue-300.cursor-pointer.hover:bg-red-700.m-1
   {:on-click fun} text])

(defn link-dispatch [rf-evt text]
  (link-fn #(rf/dispatch rf-evt) text))

(defn link-href [href text]
  [:a.bg-blue-300.cursor-pointer.hover:bg-red-700.m-1
   {:href href} text])

(defn block [& children]
  (into [:div.bg-blue-400.m-5.inline-block {:class "w-1/4"}]
        children))

(defmethod reagent-page :demo/eval [& args]
  [:div
   [:p [link-dispatch [:bidi/goto  :demo/main] "main"]]
   [:h1 "picasso eval"]

   [block
   [:p.text-4xl "picasso painter"]
    [:p "hiccup painter:"]
     [picasso-result data/hiccup]

     [:p "list-like painter:"]
     [picasso-result data/list-like]]
   
    [block
   [:p.text-4xl "picasso eval"]
   [:p "edn eval"]
   [eval-cell {:kernel :edn :code "[7 8]"}]]

])
