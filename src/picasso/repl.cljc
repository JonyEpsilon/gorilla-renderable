(ns picasso.repl
  "helper functions which should be available from the repl"
  (:require
   [picasso.protocols :refer [make Renderable render]] ; define Renderable (which has render function)
   ))

(defn render-as
  "renders text to a gorilla cell"
  [type data]
  (reify Renderable
    (render [_]
      (make type data))))

(defn text!
  "renders text to a gorilla cell"
  [text]
  (render-as :text text))

(defn html!
  "renders html to a gorilla cell
   if (type string) assumes it is valid html and renders as is
   otherwise will assume it is valid hiccup syntax and render hiccup syntax"
  [html-as-string]
  (render-as :html html-as-string))

(defn R!
  "renders a pinkie hiccup spec"
  [pinkie-spec]
  (render-as :pinkie pinkie-spec))

(comment
  (render (R! '[:h1 "hello, world"]))
  (render (R! '[clock "hello, world"]))

  (render (html! [:h1 "hello"]))

  ; comment end
  )