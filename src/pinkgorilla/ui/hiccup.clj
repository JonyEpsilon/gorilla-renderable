(ns pinkgorilla.ui.hiccup
  "plugin to render hiccup-style html in pink-gorilla
   (TODO: move to own library)"
  (:require
   [hiccup.core]
   [hiccup.page]
   [pinkgorilla.ui.gorilla-renderable :refer :all] ; define Renderable (which has render function)
   ))

;; This implementation uses reify. This means we do not need a dedicated defrecord to get the rendering done.
;; So this kind of structure is cleaner.
(defn html! [hiccup-markup-or-html-string]
  "renders html to a gorilla cell
   if (type string) assumes it is valid html and renders as is
   otherwise will assume it is valid hiccup syntax and render hiccup syntax"
  (reify pinkgorilla.ui.gorilla-renderable/Renderable
    (render [_]
      {:type :html
       :content  (if (string? hiccup-markup-or-html-string)
                   hiccup-markup-or-html-string
                   (hiccup.core/html hiccup-markup-or-html-string))
       ;:value (pr-str self) ; DO NOT SET VALUE; this will fuckup loading. (at least in original gorilla)
       })))

(comment
  (render (html! [:h1 "hello"])))
