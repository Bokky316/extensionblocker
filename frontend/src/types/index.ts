// 고정 확장자 응답 타입
export interface FixedExtension {
  id: number
  name: string
  checked: boolean
}

// 커스텀 확장자 응답 타입
export interface CustomExtension {
  id: number
  name: string
  createdAt: string
}
