(ns picasso.converter
  (:require
   [picasso.protocols :refer [render]]))

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
      (contains? m :r) {:type :reagent :content {:hiccup result :map-keywords false :widget false}}
      (contains? m :R) {:type :reagent :content {:hiccup result :map-keywords true :widget true}}
      (contains? m :p/render-as) {:type :reagent :content {:hiccup result :map-keywords true :widget true}}
      :else (render result))))
