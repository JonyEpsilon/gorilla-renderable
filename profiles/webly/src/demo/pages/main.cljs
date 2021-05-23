(ns demo.pages.main
  (:require
    [taoensso.timbre :refer-macros [debug info warn error]]
   [reagent.core :as r]
   [re-frame.core :refer [dispatch subscribe]]
   [webly.web.handler :refer [reagent-page]]
   [webly.user.notifications.core :refer [add-notification]]
   [webly.user.oauth2.view :refer [tokens-view user-button]]
   [webly.user.settings.local-storage :refer [ls-get ls-set!]]
   [webly.user.emoji :refer [emoji]]))

(defn show-dialog-demo []
  (dispatch [:modal/open [:h1.bg-blue-300.p-5 "dummy dialog"]
             :small]))

(defn link-fn [fun text]
  [:a.bg-blue-300.cursor-pointer.hover:bg-red-700.m-1
   {:on-click fun} text])

(defn link-dispatch [rf-evt text]
  (link-fn #(dispatch rf-evt) text))

(defn link-href [href text]
  [:a.bg-blue-300.cursor-pointer.hover:bg-red-700.m-1
   {:href href} text])

(defn block [& children]
  (into [:div.bg-blue-400.m-5.inline-block {:class "w-1/4"}]
        children))

(defn demo-eval []
  [block
   [:p.text-4xl "bidi routes"]
   [:p [link-dispatch [:bidi/goto :demo/eval] "eval"]]
   [:p [link-dispatch [:bidi/goto :notebook/current] "notebook"]]
 ])

(defn main []
  [:div
   [:h1 "picasso demo"]
   [:p [link-dispatch [:reframe10x-toggle] "tenx-toggle"]]  
   [demo-eval]
])

(defmethod reagent-page :demo/main [& args]
  [main])















