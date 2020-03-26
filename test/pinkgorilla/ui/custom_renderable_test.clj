(ns pinkgorilla.ui.custom-renderable-test
  (:require
   [clojure.test :refer :all]
   [pinkgorilla.ui.gorilla-renderable :as renderable :refer [Renderable render]]))

;; REIFY DUMMY

(def quick-foo
  (reify Renderable
    (render [this] "quick-foo")))

(deftest reify-dummy
  (is (= (render quick-foo) "quick-foo")))

;; CUSTOM RENDERER

(defrecord Bongo [specs])

(extend-type Bongo
  Renderable
  (render [this]
    {:type :html
     :content (:specs this)
    ;:value (pr-str self)
     }))

;; for this test, in cljs the namespace uses "-", but for clj we use "_" 
(deftest custom-dummy-renderable
  (is (= (pr-str (Bongo. {:a 1 :b "2"})) "#pinkgorilla.ui.custom_renderable_test.Bongo{:specs {:a 1, :b \"2\"}}"))
  (is (= (render (Bongo. {:a 1 :b "2"})) {:type :html :content {:a 1 :b "2"}})))

(deftest renderable-custom
  (is (= (render (Bongo. 7))
         {:type :html
          :content 7 ; "<span class='cljs-boolean'>true</span>"
          ;:value "true"
          })))
