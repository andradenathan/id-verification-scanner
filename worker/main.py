from pathlib import Path

import cv2
from insightface.app import FaceAnalysis

IMAGE_PATH = Path("./screenshot.png")
OUTPUT_DIR = Path("output")
OUTPUT_DIR.mkdir(exist_ok=True)


def main():
    if not IMAGE_PATH.exists():
        raise FileNotFoundError(f"Image not found: {IMAGE_PATH}")

    app = FaceAnalysis(name="buffalo_l", providers=["CPUExecutionProvider"])
    app.prepare(ctx_id=0, det_size=(640, 640))

    img = cv2.imread(str(IMAGE_PATH))
    if img is None:
        raise RuntimeError("Fail to read image.")

    img_rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

    faces = app.get(img_rgb)

    if not faces:
        print("No face detected.")
        return

    face = max(faces, key=lambda f: (f.bbox[2] - f.bbox[0]) * (f.bbox[3] - f.bbox[1]))

    x1, y1, x2, y2 = map(int, face.bbox)

    pad = 20
    h, w, _ = img.shape
    x1 = max(0, x1 - pad)
    y1 = max(0, y1 - pad)
    x2 = min(w, x2 + pad)
    y2 = min(h, y2 + pad)

    face_img = img[y1:y2, x1:x2]

    output_path = OUTPUT_DIR / "face.png"
    cv2.imwrite(str(output_path), face_img)


if __name__ == "__main__":
    main()
