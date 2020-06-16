(ns picasso.protocols)

;;; This is the protocol that a type must implement if it wants to customise its rendering in Gorilla.
;;; It defines a single function, render, that should transform the value into a value that the 
;;; front-end's renderer can display.

(defprotocol Renderable
  (render [self]))

(defn make [type content]
  {:type type
   :content content
     ;:value (pr-str content)
   })

(defmulti paint :type)









