(ns ui.notebook.view.segment
  (:require
   [taoensso.timbre :as timbre :refer [debug info warn error]]
   [re-frame.core :as rf :refer [subscribe dispatch]]
   [picasso.kernel.view.eval-result :refer [eval-result-view]]
   [ui.code.goldly.codemirror-themed  :refer [codemirror-themed]]
   [ui.markdown.viewer :refer [markdown-viewer]]
   [ui.markdown.prosemirror :as prosemirror]))

(def pm-fun {:get-data (fn [id]
                         (let [s (rf/subscribe [:notebook/segment id])]
                           (if s
                             (do (info "md sub id: " id  "is: " @s)
                                 (or (get-in @s [:data]) "xxx sub was empty"))
                             "empty md")))
             :save-data (fn [id text]
                          (info "pm-text save")
                          (rf/dispatch [:doc/exec [:set-md-segment id text]]))})

(defn md-segment-view
  [{:keys [id data]} active?]
  [markdown-viewer {:on-click #(dispatch [:notebook/move :to id])} data])

(defn md-segment-edit
  [{:keys [id data]} active?]
  [prosemirror/prosemirror-reagent id pm-fun active?])

(defn md-segment
  [{:keys [id] :as segment}]
  (let [segment-active (rf/subscribe [:notebook/segment-active])]
    (fn [{:keys [id] :as segment}]
      (let [active? (= (:id @segment-active) id)]
        [:div {;:class  (str "segment md"
               ;             (if active? " selected" ""))
               :on-click #(rf/dispatch [:notebook/move :to id])}
         (if active?
           [md-segment-edit segment active?]
           [md-segment-view segment active?])]))))


;; input


(defn segment-input-code [{:keys [id data segment-active?] :as cm-opt} eval-result]
  [:div {:style {:position "relative"}} ; container for kernel absolute position
   ; kernel - one color for both dark and light themes.
   (when-let [kernel (:kernel data)] ; snippets might not define kernel
     [:span.pr-2.text-right.text-blue-600.tracking-wide.font-bold.border.border-blue-300.rounded
      {:on-click #(dispatch [:segment-active/kernel-toggle])
       :style {:position "absolute"
               :z-index 200 ; dialog is 1040 (we have to be lower)
               :top "2px" ; not too big, so it works for single-row code segments
               :right "10px"
               :width "50px"
               :height "22px"}} kernel])
   (if segment-active?
     [codemirror-themed id cm-opt]
     [codemirror-themed id cm-opt])])

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


(defn segment-output [{:keys [type] :as eval-result}]
  (case type
    :md [md-segment eval-result]
     ;:code [eval-result-view eval-result]
    [:div "output unknown type: " type "data: " (pr-str eval-result)]))

(defn segment-output-no-md [{:keys [type] :as eval-result}]
  [:div
   (case type
     :md [md-segment eval-result]
     :code [eval-result-view eval-result]
     [:p "output" type])])
