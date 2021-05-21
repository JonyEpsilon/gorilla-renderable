(ns picasso.converter
  (:require
   [picasso.protocols :refer [render make]]))

#_(defn render-renderable
    "rendering via the Renderable protocol (needs renderable project)
   (users can define their own render implementations)"
    [result]
    (let [response   {:value-response (render result)}
        ;_ (println "response: " response)
          ]
      response))

; This is used by nrepl middleware ro convert type to renderable

(defn ->picasso
  "rendering via the Renderable protocol (needs renderable project)
   (users can define their own render implementations)"
  [result]
  (let [m (meta result)]
    (cond
      (contains? m :r) (make :reagent {:hiccup result :map-keywords false})
      (contains? m :R) (make :reagent {:hiccup result :map-keywords true})
      (contains? m :p/render-as) (make :reagent {:hiccup result :map-keywords true})
      :else (render result))))
