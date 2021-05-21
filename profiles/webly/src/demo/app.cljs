(ns demo.app
  (:require
   [reagent.dom]
   [taoensso.timbre :as timbre :refer [info]]
   [webly.user.app.app :refer [webly-run!]]
   [demo.routes :refer [routes-api routes-app]]
   ; side-effects 
   [demo.events]
   [demo.pages.main]
   [demo.pages.eval]
   ))

(defn ^:export start []
  (info "picasso demo starting ...")
  (webly-run! routes-api routes-app))

