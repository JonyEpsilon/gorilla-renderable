(ns pinkgorilla.ui.module-test
  "custom components need to define a javascript-module
   module-test is a very simple sample that just renders the data as
   JSON to the dom node that is passed.
   In early state component development this is helpful."
    (:require 
     [pinkgorilla.ui.gorilla-renderable :refer :all] ; define Renderable (which has render function
     ))


(def module-test "
  define([], function () {
      return {
         version: 'module-test 0.0.3',
         render: function (id_or_domel, data) {
            var domElement = id_or_domel;
            if (typeof id_or_domel === 'string' || id_or_domel instanceof String) {
               selector = '#'+ id_or_domel;
               console.log ('module-test is rendering to selector: ' + selector);
               domElement = document.getElementById (selector); 
            } else {
               console.log ('module-test is rendering to dom-element');
            }

            var dataJson = JSON.stringify(data)
            console.log ('module-test data: ' + dataJson);

            var p = document.createElement ('p');
            var json = JSON.stringify (data);
            var textnode = document.createTextNode (dataJson);  
            //var textnode = document.createTextNode ('module-test rocks');  
            p.appendChild (textnode);
            domElement.appendChild (p);
         }
      }
  });
")

(defn test! [spec]
  "renders data as json to a gorilla cell"
  (reify Renderable
    (render [_]
      {:type :jsscript
       :content
       {:data spec
        :module module-test}
       ;:value (pr-str self) ; DO NOT SET VALUE; this will fuckup loading. (at least in original gorilla)
       })))
