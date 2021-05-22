(ns picasso.data.document)

(def document
  {:id :7c9ab23f-c32f-4879-b74c-de7835ca8ba4
   :meta {:title "demo 123"
          :tags #{:demo :simple}}
   :segments
   [{:id 1
     :type :md
     :data "# hello, world\n- iii\n- ooo"
     :state nil}
    {:id 7
     :type :code
     :data {:kernel :clj
            :code "(+ 7 7)"}
     :state nil}
    {:id 8
     :type :md
     :data "# edn kernel evals\nyes -edn for testing!"
     :state nil}
    {:id 2
     :type :code
     :data {:kernel :edn
            :code "13"}
     :state {:id 15
             :picasso {:type :hiccup
                       :content [:span {:class "clj-long"} 13]}}}
    {:id 3
     :type :code
     :data {:kernel :edn
            :code "[7 8]"}
     :state {:id 16
             :picasso {:type :list-like
                       :content {:class "clj-vector"
                                 :open "["
                                 :close "]"
                                 :separator  ", "
                                 :items [{:type :hiccup
                                          :content [:span {:class "clj-long"} 7]}
                                         {:type :hiccup
                                          :content [:span {:class "clj-long"} 8]}]
                                 :value [7 8]}}}}]})
