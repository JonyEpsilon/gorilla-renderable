(ns ui.notebook.settings
  (:require
   [taoensso.timbre :as timbre :refer [debug info warn error]]
   [re-frame.core :as rf]))

(rf/reg-sub
 :notebook/layout
 :<- [:settings] ; defined in webly
 (fn [settings _]
   (or (:layout settings) :single)))

(rf/reg-event-fx
 :notebook/layout
 (fn [_ [_ layout]]
   (rf/dispatch [:settings/set :layout layout])))

(def layouts
  [:single :horizontal :vertical :stacked])

(rf/reg-event-db
 :notebook/layout-toggle
 (fn [db _]
   (let [layout (or (get-in db [:settings :layout]) :single)
         v-indexed (map-indexed (fn [idx id] [idx id]) layouts)
         current (first
                  (filter
                   (fn [[idx id]] (= layout id))
                   v-indexed))
         current-idx (get current 0)
         new-idx (if (= current-idx 0)
                   (- (count layouts) 1)
                   (- current-idx 1))
         layout-new (get layouts new-idx)]
     (rf/dispatch [:notebook/layout layout-new])
     db)))