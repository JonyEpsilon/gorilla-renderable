(ns picasso.render.table
   (:require
    [picasso.protocols :refer [Renderable render]] ; define Renderable (which has render function)
    ))

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