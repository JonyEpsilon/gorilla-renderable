(ns pinkgorilla.ui.text
  "plugin to render text in pink-gorilla"
  (:require
   [pinkgorilla.ui.gorilla-renderable :as render] ; define Renderable (which has render function)
   ))

(defn text!
  "renders text to a gorilla cell"
  [text]
  (reify render/Renderable
    (render [_]
      {:type :text
       :content
       {:text  text}
       ;:value (pr-str self) ; DO NOT SET VALUE; this will fuckup loading. (at least in original gorilla)
       })))


