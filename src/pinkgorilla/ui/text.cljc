(ns pinkgorilla.ui.text
  "plugin to render text in pink-gorilla"
  (:require 
   [pinkgorilla.ui.gorilla-renderable :refer :all] ; define Renderable (which has render function)
   ))

(defn text! [text]
  "renders text to a gorilla cell"
  (reify Renderable
    (render [_]
      {:type :text
       :content 
         {:text  text
          }
       ;:value (pr-str self) ; DO NOT SET VALUE; this will fuckup loading. (at least in original gorilla)
       })
    ))


