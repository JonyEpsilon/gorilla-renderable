(ns pinkgorilla.ui.to-reagent-test
  (:require
   [cljs.test :refer-macros [async deftest is testing]]
   [pinkgorilla.ui.reagent-keyword :refer [keyword-to-reagent unknown-renderer]]
   ))



(deftest to-reagent-pp
  (is (= (keyword-to-reagent :BONGO :a 4)
         unknown-renderer)))