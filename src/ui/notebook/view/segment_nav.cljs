(ns ui.notebook.view.segment-nav
  (:require
   [taoensso.timbre :as timbre :refer [debug info warn error]]
   [re-frame.core :refer [subscribe dispatch]]
   [picasso.document.position :refer [segment-ids-ordered]]))

(defn segments-ordered [notebook]
  (let [segments (:segments notebook)
        segment-ids-ordered (:order notebook)]
    (vec (map #(get segments %) segment-ids-ordered))))

(defn icon [active-segment-id current-segment-id]
  (let [active? (= active-segment-id current-segment-id)]
    (if active?
      [:i.fas.fa-circle.ml-1]
      [:i.far.fa-circle.ml-1
       {:on-click #(dispatch [:notebook/move :to current-segment-id])}])))

(defn segment-nav-impl [segment-active notebook]
  (into [:div]
        (map (partial icon segment-active) (segment-ids-ordered notebook))))

(defn segment-nav []
  (let [notebook @(subscribe [:notebook])
        active-segment @(subscribe [:notebook/segment-active])
        active-segment-id (:id active-segment)]
    [segment-nav-impl active-segment-id notebook]))
