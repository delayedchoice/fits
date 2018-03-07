(ns fits.core
  (:gen-class))

(defn edit-all-files-in-directory [dir]
  (let [d (clojure.java.io/file dir)
        fs (file-seq d)
        fs (rest fs)]
    (doseq [f fs]
      (let [orig   (slurp f)
            edited (.replace orig "DATE    " "DATE_OBS")
            _ (prn "editing: " (.getName f))]
        (spit (str "edited-" (.getName f)) edited )))))

(defn -main
  [& args]
  (edit-all-files-in-directory (first args)))
