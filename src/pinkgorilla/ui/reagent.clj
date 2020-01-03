(ns pinkgorilla.ui.reagent
  "plugin to render widgets in pink-gorilla
   widgets are simply reagent components"
  (:require
   [pinkgorilla.ui.gorilla-renderable :refer :all]         ; define Renderable (which has render function)
   [clojure.walk :refer [prewalk]]))

(defn reagent! [r]
  "renders a reagent widget"
  (reify Renderable
    (render [_]
      {:type    :reagent
       :content r
       :value   (pr-str r)
       ;:value (pr-str self) ; DO NOT SET VALUE; this will fuckup loading. (at least in original gorilla)
       })))

(defn R! [r]
  "renders a reagent widget"
  (reify Renderable
    (render [_]
      {:type    :reagent-cljs
       :content {:reagent r
                 :map-kewords true}
       :map-keywords true
       :reagent r
       :value   (pr-str r)
       ;:value (pr-str self) ; DO NOT SET VALUE; this will fuckup loading. (at least in original gorilla)
       })))

(comment
  (render (reagent! '[:h1 "hello, world"]))
  (render (reagent! '[clock "hello, world"]))

  (def matrix [[1 2 3]
               [4 5 6]
               [7 8 9]])

  (use 'clojure.walk :only [prewalk])

  (defn check [x]
    (println "checking: " x " type: " (type x))
    (coll? x))

  (clojure.walk/prewalk
   #(if (check %) (inc %) %) matrix)

  (def x '[:div {:style "asdf"}
           [:h1 (str "hello world" (+ 1 1))]
           [hello "world"]])

  (def data [[1 :foo] [2 [3 [4 "abc"]] 5]])

  (defn f [x]
    (do
      (println "visiting:" x " type: " (type x))
      (println "first type is:" (type (first x)))
      (println "last type is:" (type (last x)))
      (println "first rest type is:" (type (first (rest x))))
      x))

  (walk/postwalk f data)

  (clojure.walk/prewalk #(if (check %) (f %) %) x)

  (reduce merge (rest [1 2 3]))

  (assoc [1 2 3] 0 5))
