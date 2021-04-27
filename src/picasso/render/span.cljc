(ns picasso.render.span
  (:require
   [picasso.protocols :refer [make]]))

;; A lot of things render to an HTML span, with a class to mark the type of thing. 
;; This helper constructs the rendered
;; value in that case.


(defn span-render
  [thing class]
  (make :hiccup
        [:span {:class class} (pr-str thing)]))
