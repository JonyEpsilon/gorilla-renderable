(ns picasso.kernel-clj-test
  (:require
   [clojure.test :refer [deftest is are use-fixtures]]
   [clojure.core.async :refer [<! <!! >! >!! put! chan close! go go-loop]]
   [clojure.string :as string]
   [picasso.kernel.protocol :refer [kernel-eval]]
   [picasso.default-config]  ; side-effects
   [picasso.kernel.clj] ; side-effects
   ))

(defn r= [a b]
  (= (dissoc a :id) b))

(deftest test-eval-clj
  "eval with several expressions"
  (go (are [input-clj result]
           (r= (<! (kernel-eval {:kernel :clj :code input-clj}))
               result)

        "13"
        {:result {:type :hiccup :content [:span {:class "clj-long"} "13"]}}

        "[7 8]"
        {:result
         {:type :list-like
          :content
          {:class "clj-vector"
           :open "["
           :close "]"
           :separator " "
           :items
           '({:type :hiccup, :content [:span {:class "clj-long"} "7"]}
             {:type :hiccup, :content [:span {:class "clj-long"} "8"]})
           :value "[7 8]"}}})))


