(ns ui.notebook.events)

#_(reg-event-db
   :notebook/close-dialog-or-exit-edit
   (fn [db [_]]
     (let [show? (get-in db [:modal :show?])]
       (if show?
         (dispatch [:modal/close])
         (dispatch [:notebook/set-cm-md-edit false]))
       db)))

#_(reg-event-db
   :segment-active/toggle-view
   (fn [db [_]]
     (segment-op db toggle-view-segment)))