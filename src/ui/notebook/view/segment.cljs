(ns ui.notebook.view.segment
  (:require
   [taoensso.timbre :as timbre :refer [debug info warn error]]
   [re-frame.core :as rf :refer [subscribe dispatch]]
   [ui.notebook.view.segment.markdown :refer [md-segment]]
   [ui.notebook.view.segment.eval-result :refer [er-segment]]
   [ui.notebook.view.segment.code  :refer [segment-input-code]]
   ))

;; input



(defn segment-input [{:keys [id type] :as segment}]
  (let [settings (subscribe [:settings])
        segment-active (subscribe [:notebook/segment-active])
        segment-queued (subscribe [:segment/queued? id])
        cm-md-edit? (subscribe [:notebook/edit?])]
    (fn [{:keys [id type] :as segment}]
      (let [active? (= (:id @segment-active) id)
            cm-md-edit? @cm-md-edit?
            queued? @segment-queued
            options  {:segment-active? active?}
            full? (= (:layout @settings) :single)]
        [:div.text-left.bg-gray-100 ; .border-solid
         {:id id
          :on-click #(do
                       (dispatch [:notebook/move :to id])
                       (dispatch [:notebook/set-cm-md-edit true]))
          :class (str (if queued?
                        "border border-solid border-blue-600"
                        (if active?
                          (if cm-md-edit? "border border-solid border-red-600"
                              "border border-solid border-gray-600")
                          ""))
                      (if full? " h-full" ""))}

         [:div "seg-id: " id (pr-str segment)
          (case type
           ;:code [segment-input-code options eval-result]
            :md   [:div] ; [segment-input-md options segment]
            [:p "unknown type: " type])]]))))

;; output


(defn segment-output [{:keys [type] :as seg}]
  (case type
    :md [md-segment seg]
    :code [er-segment seg]
    [:div "output unknown type: " type "data: " (pr-str seg)]))

(defn segment-output-no-md [{:keys [type] :as segment}]
  [:div
   (case type
     :md [md-segment segment]
     :code [er-segment segment]
     [:p "output" type])])
