(ns picasso.render-custom-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [picasso.protocols :refer [Renderable render]]))

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
    {:type :hiccup
     :content (:specs this)
    ;:value (pr-str self)
     }))

(deftest custom-dummy-renderable
  (is (= (pr-str (Bongo. {:a 1 :b "2"})) "#picasso.render-custom-test.Bongo{:specs {:a 1, :b \"2\"}}"))
  (is (= (render (Bongo. {:a 1 :b "2"})) {:type :hiccup :content {:a 1 :b "2"}})))

(deftest renderable-custom
  (is (= (render (Bongo. 7))
         {:type :hiccup
          :content 7 ; "<span class='cljs-boolean'>true</span>"
          ;:value "true"
          })))
