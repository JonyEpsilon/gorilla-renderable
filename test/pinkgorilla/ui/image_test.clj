(ns pinkgorilla.ui.image-test
  (:require
   [clojure.test :refer :all]
   [pinkgorilla.ui.gorilla-renderable :refer [render]]
   [pinkgorilla.ui.hiccup_renderer] ; bring the renderers into scope
   [pinkgorilla.ui.image :refer [image-view]])
  (:import java.io.File
           java.io.InputStream
           javax.imageio.ImageIO))

(deftest render-image
  (let [img (ImageIO/read (File. "test/pinkgorilla/ui/brain.jpeg"))
        view (image-view img)
        rendered (render view)]
    (is (= {:alt ""
            :type "png"
            :width 236
            :height 177}
           (dissoc view :image)))
    (is (= {:type :html}
           (dissoc rendered :content :value)))))