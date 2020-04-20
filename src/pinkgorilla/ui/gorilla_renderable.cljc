(ns pinkgorilla.ui.gorilla-renderable)

;;; This is the protocol that a type must implement if it wants to customise its rendering in Gorilla.
;;;  It defines a single function, render, that should transform the value into a value that the 
;;; front-end's renderer can display.

(defprotocol Renderable
  (render [self]))

(defn render-renderable
  "rendering via the Renderable protocol (needs renderable project)
   (users can define their own render implementations)"
  [result]
  (let [response   {:value-response (render result)}
        ;_ (println "response: " response)
        ]
    response))

(defn render-renderable-meta
  "rendering via the Renderable protocol (needs renderable project)
   (users can define their own render implementations)"
  [result]
  (let [m (meta result)]
    {:value-response
     (cond
       (contains? m :r) {:type :reagent :content {:hiccup result :map-keywords false :widget false}}
       (contains? m :R) {:type :reagent :content {:hiccup result :map-keywords true :widget true}}
       (contains? m :render-as) {:type :reagent :content {:hiccup result :map-keywords true :widget true}}
       :else (render result))}))