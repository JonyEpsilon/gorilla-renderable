(ns picasso.repl
  "helper functions which should be available from the repl"
  (:require
   [picasso.protocols :refer [make Renderable render]] ; define Renderable (which has render function)
   ))

(defn text!
  "renders text to a gorilla cell"
  [text]
  (reify Renderable
    (render [_]
      (make :text text))))

(defn html!
  "renders html to a gorilla cell
   if (type string) assumes it is valid html and renders as is
   otherwise will assume it is valid hiccup syntax and render hiccup syntax"
  [html-as-string]
  (reify Renderable
    (render [_]
      (make :html html-as-string))))

(defn R!
  "renders a pinkie hiccup spec"
  [r]
  (reify Renderable
    (render [_]
      (make :pinkie r))))

(defn R [data]
  (R! data))





;; table-view


(defrecord TableView [contents opts])

(defn table-view [contents & opts]
  (TableView. contents opts))

(defn- list-like
  [data value open close separator]
  {:type :list-like
   :open open
   :close close
   :separator separator
   :items data
   :value value})

(extend-type TableView
  Renderable
  (render [self]
    (let [contents (:contents self)
          opts-map (apply hash-map (:opts self))
          rows (map (fn [r] (list-like (map render r) (pr-str r) "<tr><td>" "</td></tr>" "</td><td>")) contents)
          heading (if-let [cols (:columns opts-map)]
                    [(list-like (map render cols) (pr-str cols) "<tr><th>" "</th></tr>" "</th><th>")]
                    [])
          body (list-like (concat heading rows) (pr-str self) "<center><table>" "</table></center>" "\n")]
      body)))

(comment
  (render (R! '[:h1 "hello, world"]))
  (render (R! '[clock "hello, world"]))

  (render (html! [:h1 "hello"]))

  ; comment end
  )