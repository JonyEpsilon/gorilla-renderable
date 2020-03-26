(ns pinkgorilla.ui.helper
  "helper functions which should be available from the repl"
  (:require
   [pinkgorilla.ui.gorilla-renderable :refer [Renderable render]] ; define Renderable (which has render function)
   ))

(defn text!
  "renders text to a gorilla cell"
  [text]
  (reify Renderable
    (render [_]
      {:type :text
       :content
       {:text  text}
       ;:value (pr-str self) ; DO NOT SET VALUE; this will fuckup loading. (at least in original gorilla)
       })))

(defn R!
  "renders a reagent widget"
  [r]
  (reify Renderable
    (render [_]
      {:type    :reagent
       :content {:hiccup r
                 :map-kewords true
                 :widget  true}
       :value   (pr-str r)
       ;:value (pr-str self) ; DO NOT SET VALUE; this will fuckup loading. (at least in original gorilla)
       })))

(defn html!
  "renders html to a gorilla cell
   if (type string) assumes it is valid html and renders as is
   otherwise will assume it is valid hiccup syntax and render hiccup syntax"
  [html-as-string]
  ^:R [:html html-as-string])



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