import React, { useRef } from 'react';
import '../css/Certificate.css';
import { Button, Card } from 'react-bootstrap';
import html2canvas from 'html2canvas';

const Certificate = ({ courseTitle, userName, progress, countContent, gpa }) => {
    const certificateRef = useRef();

    const clickPrint = () => {
        if (progress !== countContent) {
            alert("Có vẻ bạn chưa hoàn thành hết khóa học.");
            return;
        }

        html2canvas(certificateRef.current, {
            backgroundColor: '#ffffff',
            useCORS: true,
            logging: true
        }).then(canvas => {
            const imgData = canvas.toDataURL('image/png');

            // Create a link element and trigger the download
            const link = document.createElement('a');
            link.href = imgData;
            link.download = 'certificate.png';
            link.click();
        }).catch(error => {
            console.error('Error generating PNG:', error);
        });
    };

    return (
        <>
            <Card className="mb-4">
                <Card.Body className="bg-info text-white text-center">
                    <h3 className="my-2">NHẬN LẤY THÀNH QUẢ CỦA BẢN THÂN</h3>
                </Card.Body>
            </Card>

            <div className='certificateContainer' ref={certificateRef}>
                <img src='https://res.cloudinary.com/dh1irfap0/image/upload/v1725455598/Untitled_design_jidaet.png' height={'auto'} width={'80%'} alt="Certificate Background" />
                <div className='contentCertificate'>
                    <h1>{userName}</h1>
                </div>
                <div className='contentCertificate'>
                    <h2>{courseTitle}</h2>
                </div>
                <div className='contentCertificate'>
                    <h3>{gpa}</h3>
                </div>
            </div>

            <Button className='buttonPrint' onClick={clickPrint}>
                Tải chứng chỉ
            </Button>
        </>
    );
};

export default Certificate;
