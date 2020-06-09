(ns pinkie.converter)

;;; This is the protocol that a type must implement if it wants to customise its rendering in Gorilla.
;;;  It defines a single function, render, that should transform the value into a value that the 
;;; front-end's renderer can display.

(defprotocol Pinkie
  (to-pinkie [self]))

(defn ->pinkie
  "rendering via the Renderable protocol (needs renderable project)
   (users can define their own render implementations)"
  [data]
  (let [m (meta data)]
    (cond
      (contains? m :r) ^{:map-keywords false} data
      (contains? m :R) ^{:map-keywords true} data
      (contains? m :p/render-as) (let [tag (:p/render-as m)]
                                   ^{:map-keywords true} [tag data])
      :else (to-pinkie data))))