(ns ui.notebook.view.segment.code
  (:require
   [taoensso.timbre :as timbre :refer [debug info warn error]]
   [re-frame.core :as rf]
   [ui.code.goldly.codemirror-themed  :refer [codemirror-themed]]))

(def cm-fun {:get-data (fn [id]
                         (let [s (rf/subscribe [:notebook/segment id])]
                           (if s
                             (do (info "cm sub id: " id  "is: " @s)
                                 (or (get-in @s [:data :code]) "xxx sub was empty"))
                             "empty code")))
             :save-data (fn [id text]
                          (info "cm-text save")
                          (rf/dispatch [:doc/exec [:set-code-segment id text]]))

             :cm-events (fn [cm-evt]
                          (info "cm event " cm-evt))})

(defn segment-code-edit [{:keys [active?] :as cm-opts} {:keys [id data] :as seg}]
  [:div {:style {:position "relative"}} ; container for kernel absolute position
   ; kernel - one color for both dark and light themes.
   (when-let [kernel (:kernel data)] ; snippets might not define kernel
     [:span.pr-2.text-right.text-blue-600.tracking-wide.font-bold.border.border-blue-300.rounded
      {:on-click #(rf/dispatch [:segment-active/kernel-toggle])
       :style {:position "absolute"
               :z-index 200 ; dialog is 1040 (we have to be lower)
               :top "2px" ; not too big, so it works for single-row code segments
               :right "10px"
               :width "50px"
               :height "22px"}} kernel])
   (if active?
     [codemirror-themed id cm-fun cm-opts]
     [codemirror-themed id cm-fun cm-opts])])

(defn segment-code [{:keys [view-only] :as nb-settings} {:keys [id type] :as seg}]
  (let [settings (rf/subscribe [:settings])
        segment-active (rf/subscribe [:notebook/segment-active])
        segment-queued (rf/subscribe [:segment/queued? id])
        cm-md-edit? (rf/subscribe [:notebook/edit?])]
    (fn [{:keys [view-only] :as nb-settings} {:keys [id type] :as seg}]
      (let [active? (= (:id @segment-active) id)
            queued? @segment-queued
            full? (= (:layout @settings) :single)
            cm-opts  {:active? active?
                      :full? full?
                      :view-only view-only}]

        [:div.text-left.bg-gray-100 ; .border-solid
         {:id id
          :on-click #(do
                       (rf/dispatch [:notebook/move :to id]))
          :class (str (if queued?
                        "border border-solid border-blue-600"
                        (if active?
                          (if cm-md-edit? "border border-solid border-red-600"
                              "border border-solid border-gray-600")
                          ""))
                      (if full? " h-full" ""))}

         [segment-code-edit cm-opts seg]]))))