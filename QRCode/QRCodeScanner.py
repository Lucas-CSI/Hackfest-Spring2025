import cv2
from pyzbar.pyzbar import decode

def scan_qr_code():
    cap = cv2.VideoCapture(0)
    if not cap.isOpened():
        raise IOError("Cannot open webcam")

    while True:
        ret, frame = cap.read()
        if not ret:
            break

        for barcode in decode(frame):
            data = barcode.data.decode('utf-8')
            with open("out.txt", "w") as file:
                file.write(data)
            '''print("QR Code detected:", data)
            pts = barcode.polygon
            if len(pts) == 4:
                cv2.line(frame, pts[0], pts[1], (0, 255, 0), 2)
                cv2.line(frame, pts[1], pts[2], (0, 255, 0), 2)
                cv2.line(frame, pts[2], pts[3], (0, 255, 0), 2)
                cv2.line(frame, pts[3], pts[0], (0, 255, 0), 2)'''
            
        cv2.imshow('QR Code Scanner', frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    cap.release()
    cv2.destroyAllWindows()

if __name__ == "__main__":
    scan_qr_code()