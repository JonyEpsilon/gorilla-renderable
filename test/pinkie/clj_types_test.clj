(ns pinkie.clj-types-test
  (:require
   [clojure.test :refer :all]
   [pinkie.converter :refer [Pinkie to-pinkie]]
   [pinkie.clj-types] ; bring the renderers into scope
   ))

; Type Tests	array? fn? number? object? string?
; instance?
; 	fn?  ifn?

(deftest renderable-nil
  (is (= (to-pinkie nil)
         [:span {:class "clj-nil"} "nil"])))

(deftest renderable-keyword
  (is (= (to-pinkie :test)
         [:span {:class "clj-keyword"} ":test"])))

(deftest renderable-symbol
  (is (= (to-pinkie (symbol "s"))
         [:span {:class "clj-symbol"} "s"])))

(deftest renderable-string
  (is (= (to-pinkie "s")
         [:span {:class "clj-string"} "\"s\""])))

(deftest renderable-char
  (is (= (to-pinkie \c)
         [:span {:class "clj-char"} "\\c"])))

(deftest renderable-number
  (is (= (to-pinkie 13)
         [:span {:class "clj-long"} "13"])))

(deftest renderable-bool
  (is (= (to-pinkie true)
         [:span {:class "clj-boolean"} "true"])))

;; awb99: I am too lazy to implement this test, especially since the
;; list-alike rendering needs refactoring
(deftest renderable-map
  (is (= (to-pinkie {:a 1 :b 2})
         [:div
          "{"
          [:div {:class "clj-map"}
           [:div "["
            [:div {:class "clj-vector"}
             [:span {:class "clj-keyword"} ":a"]
             [:span {:class "clj-long"} "1"]]
            "]"]
           [:div
            "["
            [:div {:class "clj-vector"}
             [:span {:class "clj-keyword"} ":b"]
             [:span {:class "clj-long"} "2"]]
            "]"]]
          "}"])))

(defrecord MyRecord [r])


;; TODO awb99: this works on cljs, but not on clj.


#_(deftest renderable-catch-all
    (let [u (MyRecord. 3)]
  ;(println "type is: " (type u))
      (is (= (to-pinkie u)
             {:type :html
              :content [:span {:class "clj-unknown"} "#pinkgorilla.ui.core-test.MyRecord{:r 3}"]
              :value "#pinkgorilla.ui.core-test.MyRecord{:r 3}"}))))