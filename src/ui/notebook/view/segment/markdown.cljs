(ns ui.notebook.view.segment.markdown
  (:require
   [taoensso.timbre :as timbre :refer [debug info warn error]]
   [re-frame.core :as rf]
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
  [markdown-viewer {:on-click #(rf/dispatch [:notebook/move :to id])} data])

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

