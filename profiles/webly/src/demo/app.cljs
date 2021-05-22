(ns demo.app
  (:require
   [reagent.dom]
   [taoensso.timbre :as timbre :refer [info]]
   [webly.user.app.app :refer [webly-run!]]
   [demo.routes :refer [routes-api routes-app]]
   ; side-effects 
   [picasso.default-config]
   [demo.events]
   [demo.pages.main]
   [demo.pages.eval]
   [demo.pages.doc]
   ))

(defn ^:export start []
  (info "picasso demo starting ...")
  (webly-run! routes-api routes-app))

