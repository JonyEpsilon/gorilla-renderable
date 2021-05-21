(ns demo.events
  (:require
   [taoensso.timbre :as timbre :refer [info warn]]
   [re-frame.core :refer [reg-event-db dispatch]]))

(reg-event-db
 :demo/start
 (fn [db [_]]
   (info "picasso demo started.")
   (dispatch [:webly/status :running])
   db))



