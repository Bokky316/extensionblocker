import axios from 'axios'

/**
 * .env 또는 배포 환경변수에서 API base URL을 불러옵니다.
 * - 예시: http://15.165.114.113/api
 * - 반드시 VITE_ 접두사 사용
 */
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

if (!API_BASE_URL) {
  throw new Error('VITE_API_BASE_URL is not defined in .env')
}

/**
 * 공통 axios 인스턴스
 */
export const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

/**
 * 백엔드 응답 포맷 타입 정의
 * {
 *   code: number,
 *   message: string,
 *   data: T
 * }
 */
export type ApiResponse<T> = {
  code: number
  message: string
  data: T
}
