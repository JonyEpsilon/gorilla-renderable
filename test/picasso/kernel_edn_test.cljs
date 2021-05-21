(ns picasso.kernel-edn-test
  (:require
   [cljs.test :refer [deftest are]]
   [cljs.core.async :refer [<!] :refer-macros [go]]
   [picasso.kernel.protocol :refer [kernel-eval]]
   [picasso.default-config]  ; side-effects
   [picasso.kernel.edn] ; side-effects
   ))

(defn r= [a b]
  (= (dissoc a :id) b))

(deftest test-eval-edn
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


