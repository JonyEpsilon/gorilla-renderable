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
    {:value-response
     (cond
       (contains? m :r) {:type :reagent :content {:hiccup data :map-keywords false :widget false}}
       (contains? m :R) {:type :reagent :content {:hiccup data :map-keywords true :widget true}}
       (contains? m :p/render-as) {:type :reagent :content {:hiccup data :map-keywords true :widget true}}
       :else (to-pinkie data))}))