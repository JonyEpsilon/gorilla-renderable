(ns picasso.render.list-like
  (:require
   [picasso.protocols :refer [make render]]))

(defn list-like-render [options entry]
  (make :list-like
        (merge options
               {:items (map render entry) ; this calls a multi-method
                :value (pr-str entry)})))
