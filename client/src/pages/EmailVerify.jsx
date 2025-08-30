import { Link } from "react-router-dom";
import { assets } from "../assets/assets";

const EmailVerify = () => {
  return (
    <div
      className="email-verify-container d-flex align-items-center justify-content-center vh-100 position-relative"
      style={{
        background: "linear-gradient(90deg, #6a5af9, #8268f9)",
        borderRadius: "0", // fixed from 'none'
      }}
    >
      <Link
        to="/"
        className="position-absolute top-0 start-0 p-4 d-flex align-items-center gap-2"
      >
        <img src={assets.logo} alt="logo" height={32} width={32} />
        <span className="fs-4 fw-semibold text-light">Authify</span>
      </Link>

      <div className="p-5 rounded-4 shadow bg-white" style={{width:"400px"}}>
        <h4 className="text-center fw-bold mb-2">Email verify OTP</h4>
        <p className="text-center mb-4">
            Enter the 6-digit code sent to your email.
        </p>
        <div className="d-flex justify-content-between gap-2 mb-4 text-center text-white-50 mb-2">
            {[...Array(6)].map((_,i)=>(
                <input 
                key={i}
                type="text" maxLength={1} className="form-control text-center fs-4 otp-input" 
                />
            ))}
        </div>
        <button className="btn btn-primary w-100 fw-semibold">
            Verify email
        </button>
      </div>
    </div>
  );
};

export default EmailVerify;
