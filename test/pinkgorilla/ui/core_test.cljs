(ns pinkgorilla.ui.core-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [pinkgorilla.ui.gorilla-renderable :refer [render]]
   [pinkgorilla.ui.rendererCLJS]
 ))

; Type Tests	array? fn? number? object? string?
; instance?
; 	fn?  ifn?

(deftest renderable-nil
  (is (= (render nil)
         {:type :html
          :content "<span class='cljs-nil'>nil</span>"
          :value ""})))

(deftest renderable-keyword
  (is (= (render :test)
         {:type :html
          :content "<span class='cljs-keyword'>:test</span>"
          :value ":test"})))

(deftest renderable-symbol
  (is (= (render (symbol "s"))
         {:type :html
          :content "<span class='cljs-symbol'>s</span>"
          :value "s"})))

(deftest renderable-string
  (is (= (render "s")   
         {:type :html
          :content "<span class='cljs-string'>&quot;s&quot;</span>"
          :value "\"s\""} )))

#_(deftest renderable-char
  (println "type is: " (type (char "c")))
  (is (= (render (char "c"))
         {:type :html
          :content "<span class='cljs-char'>&quot;c&quot;</span>"
          :value "\"c\""})))

(deftest renderable-number
  (is (= (render 13)
         {:type :html
          :content "<span class='cljs-number'>13</span>"
          :value "13"})))

(deftest renderable-bool
  (is (= (render true)
         {:type :html
          :content "<span class='cljs-boolean'>true</span>"
          :value "true"})))

;; awb99: I am too lazy to implement this test, especially since the
;; list-alike rendering needs refactoring
#_(deftest renderable-map
  (is (= (render {:a 1 :b 2})
         {:type :html
          :content "<span class='cljs-map'>true</span>"
          :value "true"})))


(defrecord MyRecord [r])

(deftest renderable-catch-all
  (def u (MyRecord. 3))
  ;(println "type is: " (type u))
  (is (= (render u)
         {:type :html
          :content "<span class='cljs-unknown'>#pinkgorilla.ui.core-test.MyRecord{:r 3}</span>"
          :value "#pinkgorilla.ui.core-test.MyRecord{:r 3}"})))

